## Change log
----------------------

Version 1.3-SNAPSHOT
-------------


Version 1.2
-------------

### Added
- **OcrLanguage**: Introduced a new enum `OcrLanguage` that provides a list of language options for Tesseract OCR with corresponding language codes. Each enum constant represents a language (e.g., English, German, French), storing both a display name and a Tesseract-compatible language code.
    - Added support for languages including English, German, French, Spanish, Italian, Dutch, Portuguese, Russian, Chinese (Simplified and Traditional), Japanese, Korean, and Greek.
    - This enum is designed to integrate with the UI, allowing users to select a language for OCR processing.

CHANGED:

- update gradle to new version 8.11
- update of dependency io.github.astrapi69:file-worker to new version 19.0
- update test dependency nl.jqno.equalsverifier:equalsverifier to new version 3.17.3

Version 1.1
-------------

ADDED:

- new class ImagePdfToTextExtensions that provides conversion only with tess4j library
- new dependency org.slf4j:slf4j-api in version 2.1.0-alpha1 for logging
- new dependency org.slf4j:jul-to-slf4j in version 2.1.0-alpha1 for logging
- new dependency ch.qos.logback:logback-classic in version 2.1.0-alpha1 for logging
- new bundle for logging dependencies
- new method that checks if tesseract is installed
- new method in ImagePdfToTextExtensions that gets the text content of a list of image files with tess4j library
- new method in ImagePdfToTextExtensions that gets the text files from the givenlist of image files
- new method in ImagePdfToTextExtensions that converts the PDF file to a text file that is encapsulated in a result object

CHANGED:

- update gradle to new version 8.11-rc-1
- update of gradle-plugin dependency 'com.diffplug.spotless:spotless-plugin-gradle' to new version 7.0.0.BETA4
- update of gradle-plugin dependency with id 'io.freefair.lombok' to new patch version 8.10.2
- update of gradle-plugin dependency with id 'org.ajoberstar.grgit' to new patch version 5.3.0
- update of gradle-plugin dependency with id 'nl.littlerobots.version-catalog-update' to new patch version 0.8.5
- update of dependency file-worker to new version 18.1
- update of dependency silly-io to new version 3.2
- update of dependency logback-classic to new version 1.5.12
- update of test dependency silly-collection to new major version 28.1
- update of test dependencies mockito-core to new version 5.14.2
- update of test dependencies junit-jupiter-* to new version 5.11.3
- moved PdfToTextExtensions to its own package 'io.github.astrapisixtynine.pdf.to.text.pdfbox'

Version 1.0
-------------

ADDED:

- new CHANGELOG.md file created

Notable links:
[keep a changelog](http://keepachangelog.com/en/1.0.0/) Don’t let your friends dump git logs into changelogs
