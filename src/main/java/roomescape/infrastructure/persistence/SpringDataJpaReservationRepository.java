package roomescape.infrastructure.persistence;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import roomescape.domain.reservation.Reservation;

interface SpringDataJpaReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
              SELECT r.theme.id
              FROM Reservation r
              WHERE r.date BETWEEN :startDate AND :endDate
              GROUP BY r.theme.id
              ORDER BY count(*) DESC
              LIMIT 10
            """
    )
    List<Long> findThemeReservationCountsForDate(LocalDate startDate, LocalDate endDate);

    @Query("SELECT r from Reservation r WHERE r.date = :date AND r.theme.id = :themeId")
    List<Reservation> findByDateAndThemeId(LocalDate date, Long themeId);

    @Query("SELECT EXISTS (SELECT 1 FROM Reservation r WHERE r.time.id = :id)")
    boolean existsByTimeId(Long id);

    @Query("SELECT EXISTS (SELECT 1 FROM Reservation r WHERE r.theme.id = :id)")
    boolean existsByThemeId(Long id);

    @Query("SELECT EXISTS " +
           "(SELECT 1 from Reservation r WHERE r.date = :date AND r.time.id = :timeId AND r.theme.id = :themeId)")
    boolean existsByDateAndTimeIdAndThemeId(LocalDate date, Long timeId, Long themeId);
}