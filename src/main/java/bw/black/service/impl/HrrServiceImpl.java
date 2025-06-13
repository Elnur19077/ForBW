package bw.black.service.impl;

import bw.black.dto.request.HrrRequest;
import bw.black.dto.response.HrrResponse;
import bw.black.repository.HrrRepository;
import bw.black.service.HrrService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class HrrServiceImpl implements HrrService {
    private final HrrRepository hrrRepository;

    public HrrServiceImpl(HrrRepository hrrRepository) {
        this.hrrRepository = hrrRepository;
    }

    @Override
    public List<HrrResponse> getAttendance(HrrRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String startDateStr = request.getStartDate();
        String endDateStr = request.getEndDate();

        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        List<Object[]> results = hrrRepository.getAttendance(formattedStartDate, formattedEndDate);

        return results.stream()
                .map(obj -> new HrrResponse(
                        (String) obj[0],                     // personId
                        (String) obj[1],                     // name
                        (String) obj[2],                     // department
                        ((Timestamp) obj[3]),                // attendanceDate
                        ((Timestamp) obj[4])                 // firstCheckIn
                ))
                .collect(Collectors.toList());
    }
}

