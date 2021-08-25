/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.logging;

import static com.google.cloud.logging.K8sContainerUtil.getContainerNameFromHostName;
import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class K8sContainerUtilTest {

  @Test
  public void testGetContainerNameProperlyFormatted() {
    assertThat(getContainerNameFromHostName("test-one-68449bf55d-44pdr")).isEqualTo("test-one");
    assertThat(getContainerNameFromHostName("test-again-d667d8f5-rlxz5")).isEqualTo("test-again");
    assertThat(getContainerNameFromHostName("test-again-again-again-d667d8f5-rlxz5"))
        .isEqualTo("test-again-again-again");
    assertThat(getContainerNameFromHostName("test-test--44pdr")).isEqualTo("test-test");
  }

  @Test
  public void testGetContainerNameWrongFormat() {
    assertThat(getContainerNameFromHostName("test-one-68449bf55d-68449bf55d"))
        .isEqualTo("test-one-68449bf55d-68449bf55d");
    assertThat(getContainerNameFromHostName("test-again-d667d8f5-rz5"))
        .isEqualTo("test-again-d667d8f5-rz5");
    assertThat(getContainerNameFromHostName("test-host")).isEqualTo("test-host");
    assertThat(getContainerNameFromHostName("test-host-something"))
        .isEqualTo("test-host-something");
    assertThat(getContainerNameFromHostName("test")).isEqualTo("test");
    assertThat(getContainerNameFromHostName("x--x")).isEqualTo("x--x");
  }

  @Test
  public void testGetContainerNameNullOrEmpty() {
    assertThat(getContainerNameFromHostName(null)).isEqualTo("unknown");
    assertThat(getContainerNameFromHostName("")).isEqualTo("unknown");
  }
}
