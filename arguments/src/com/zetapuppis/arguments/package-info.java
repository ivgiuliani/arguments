/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This package contains utility methods and classes for parsing command
 * line arguments.
 *
 * <p>Two different parsers are offered:
 * <ul>
 *     <li><i>positional keyword parser</i>: parses command line arguments
 *         considering only the order of the arguments in the
 *         command line. This type of parsing is offered through
 *         {@link PositionalParser}.
 *         </li>
 *     <li><i>switch-based parser</i>: parses command line arguments
 *         in switch-based format (ex: <code>--option</code>).
 *         This type of parsing is offered through {@link SwitchParser}.
 *     </li>
 * </ul>
 *
 * <p>For normal usage neither of these parsers should be used directly,
 * instead you should always use {@link CommandLineParser} which
 * combines the two parses above and handles all the edge cases for
 * you. You should use the other two parses only if you have special
 * requirements.
 *
 * @see CommandLineParser
 * @see PositionalParser
 * @see SwitchParser
 */
@ParametersAreNonnullByDefault
package com.zetapuppis.arguments;

import javax.annotation.ParametersAreNonnullByDefault;
