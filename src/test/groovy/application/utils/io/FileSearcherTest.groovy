package application.utils.io

import spock.lang.Specification

class FileSearcherTest extends Specification {

    def "search returns file path when file is found"() {
        given:
        String path = "test.yaml"
        FileSearcher fileSearcher = new FileSearcher()

        when:
        String result = fileSearcher.search(path)

        then:
        result != null
        result.endsWith("test.yaml")
    }

    def "search throws FileNotFoundException when file is not found"() {
        given:
        String path = "nonExistingFile.txt"
        FileSearcher fileSearcher = new FileSearcher()

        when:
        fileSearcher.search(path)

        then:
        thrown(FileNotFoundException)
    }

    def "search handles paths with multiple slashes correctly"() {
        given:
        String path = "dir/subdir/test.yaml"
        FileSearcher fileSearcher = new FileSearcher()

        when:
        String result = fileSearcher.search(path)

        then:
        result != null
        result.endsWith("test.yaml")
    }

    def "search throws IllegalArgumentException for empty path"() {
        given:
        String path = ""
        FileSearcher fileSearcher = new FileSearcher()

        when:
        fileSearcher.search(path)

        then:
        thrown(IllegalArgumentException)
    }

    def "search throws IllegalArgumentException for null path"() {
        given:
        String path = null
        FileSearcher fileSearcher = new FileSearcher()

        when:
        fileSearcher.search(path)

        then:
        thrown(IllegalArgumentException)
    }
}