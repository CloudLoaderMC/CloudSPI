package ml.cloudmc.cloudspi.locating;

import ml.cloudmc.cloudspi.Environment;

import java.nio.file.Path;

/**
 * Functional interface for generating a custom {@link IModLocator} from a directory, with a specific name.
 *
 * FML provides this factory at {@link Environment.Keys#MODDIRECTORYFACTORY} during
 * locator construction.
 */
public interface IModDirectoryLocatorFactory {
    IModLocator build(Path directory, String name);
}
