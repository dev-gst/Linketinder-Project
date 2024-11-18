package application.utils.io

import application.utils.validation.ParamValidation

class FileSearcher {

    static String search(String path) {
        ParamValidation.requireNonBlank(path, "path must not be blank")

        String fileName = getFileName(path)
        URL resourcePath = FileSearcher.class.getClassLoader().getResource(fileName)
        if (resourcePath != null) {
            return resourcePath.getPath()
        }

        throw new FileNotFoundException("$fileName not found")
    }

    private static String getFileName(String path) {
        return path.split("/").last()
    }
}
