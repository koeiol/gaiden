/*
 * Copyright 2013 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gaiden

import spock.lang.AutoCleanup

import java.nio.file.Files

class GaidenConfigSpec extends GaidenSpec {

    @AutoCleanup("deleteDir")
    def gaidenConfigFile = Files.createTempFile("config", "groovy")

    def "initialize the configuration with GaidenConfig.groovy"() {
        given:
            gaidenConfigFile.write '''
            | title = "Test Title"
            | templateFilePath = "test/templates/layout.html"
            | tocFilePath = "test/pages/toc.groovy"
            | pagesDirectoryPath = "test/pages"
            | assetsDirectoryPath = "test/assets"
            | outputDirectoryPath = "test/build/html"
            | inputEncoding = "UTF-8"
            | outputEncoding = "UTF-8"
            '''.stripMargin()
            def gaidenConfig = new GaidenConfig()
            gaidenConfig.pagesDirectory

        when:
            gaidenConfig.initialize(gaidenConfigFile)

        then:
            gaidenConfig.title == "Test Title"
            gaidenConfig.templateFile == gaidenConfig.projectDirectory.resolve("test/templates/layout.html")
            gaidenConfig.pagesDirectory == gaidenConfig.projectDirectory.resolve("test/pages")
            gaidenConfig.assetsDirectory == gaidenConfig.projectDirectory.resolve("test/assets")
            gaidenConfig.outputDirectory == gaidenConfig.projectDirectory.resolve("test/build/html")
            gaidenConfig.inputEncoding == "UTF-8"
            gaidenConfig.outputEncoding == "UTF-8"
    }
}
