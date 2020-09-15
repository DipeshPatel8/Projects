package ca.qc.johnabbott.cs406.profiler;

/**
 * Represents a profiler error.
 */
public class ProfilerException extends RuntimeException {

    public ProfilerException() {
    }

    public ProfilerException(String message) {
        super(message);
    }
}
