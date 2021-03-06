/*
 * Copyright 2014 the original author or authors
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

package gaiden.command

import gaiden.exception.GaidenException
import gaiden.message.MessageSource
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@CompileStatic
class UnknownCommand implements GaidenCommand {

    final String name = "unknown"

    @Autowired
    MessageSource messageSource

    @Autowired
    List<UsageAwareCommand> commands

    @Override
    void execute(List<String> options) {
        throw new GaidenException(allUsage)
    }

    @PackageScope
    String getAllUsage() {
        def max = commands.max { UsageAwareCommand command -> command.usage.size() }.usage.size()

        def usages = commands.collect { UsageAwareCommand command ->
            String.format "    %-${max}s  %s\n", [command.usage, command.description] as Object[]
        }.join('')

        messageSource.getMessage('usage', [
            messageSource.getMessage('usage.main', [usages])
        ])
    }
}
