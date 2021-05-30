package team14.airbnb.exception;

public class DuplicatedReservationException extends BadRequestException {
    public DuplicatedReservationException() {
        super("해당 기간에 이미 예약되어 있습니다.");
    }

    public DuplicatedReservationException(String message) {
        super(message);
    }

    public DuplicatedReservationException(Throwable cause) {
        super(cause);
    }
}
