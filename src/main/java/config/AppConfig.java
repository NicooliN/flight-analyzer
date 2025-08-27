package config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AppConfig {
    private final String inputFile;
    private final boolean interactiveMode;
    private final boolean verboseLogging;
}