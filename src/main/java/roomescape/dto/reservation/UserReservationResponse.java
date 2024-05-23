package roomescape.dto.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.waiting.Waiting;

public record UserReservationResponse(
        Long reservationId,
        String theme,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime time,
        String status
) {

    public static UserReservationResponse from(Reservation reservation) {
        return new UserReservationResponse(
                reservation.getId(),
                reservation.getTheme().getName(),
                reservation.getDate(),
                reservation.getTime().getStartAt(),
                reservation.getState().getDescription()
        );
    }

    public static UserReservationResponse from(Waiting waiting) {
        return new UserReservationResponse(
                waiting.getId(),
                waiting.getReservation().getTheme().getName(),
                waiting.getReservation().getDate(),
                waiting.getReservation().getTime().getStartAt(),
                "예약 대기"
        );
    }
}
