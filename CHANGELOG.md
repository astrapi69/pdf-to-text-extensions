## Change log
----------------------

Version 1.1-SNAPSHOT
-------------

ADDED:

- new class ImagePdfToTextExtensions that provides conversion only with tess4j library
- new dependency org.slf4j:slf4j-api in version 2.1.0-alpha1 for logging
- new dependency org.slf4j:jul-to-slf4j in version 2.1.0-alpha1 for logging
- new dependency ch.qos.logback:logback-classic in version 2.1.0-alpha1 for logging
- new bundle for logging dependencies
- new method that checks if tesseract is installed
- new method in ImagePdfToTextExtensions that gets the text content of a list of image files with tess4j library

CHANGED:

- update gradle to new version 8.11-rc-1
- update of gradle-plugin dependency 'com.diffplug.spotless:spotless-plugin-gradle' to new version 7.0.0.BETA3
- update of gradle-plugin dependency with id 'io.freefair.lombok' to new patch version 8.10.2
- update of gradle-plugin dependency with id 'org.ajoberstar.grgit' to new patch version 5.3.0
- update of dependency file-worker to new version 18.0
- update of dependency silly-io to new version 3.2
- update of dependency logback-classic to new version 1.5.11
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
