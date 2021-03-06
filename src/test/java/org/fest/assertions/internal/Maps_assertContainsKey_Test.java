/*
 * Created on Jun 3, 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * 
 * Copyright @2010-2012 the original author or authors.
 */
package org.fest.assertions.internal;

import static org.fest.assertions.data.MapEntry.entry;
import static org.fest.assertions.error.ShouldContainKey.shouldContainKey;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.actualIsNull;
import static org.fest.assertions.test.MapFactory.map;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.assertions.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.test.ExpectedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for <code>{@link Maps#assertContainsKey(AssertionInfo, Map, Object)}</code>.
 * 
 * @author Nicolas François
 */
public class Maps_assertContainsKey_Test {

  private static Map<String, String> actual;

  @Rule
  public ExpectedException thrown = none();

  private Failures failures;
  private Maps maps;

  @SuppressWarnings("unchecked")
  @BeforeClass
  public static void setUpOnce() {
    actual = (Map<String, String>) map(entry("name", "Yoda"), entry("color", "green"), entry(null, null));
  }

  @Before
  public void setUp() {
    failures = spy(new Failures());
    maps = new Maps();
    maps.failures = failures;
  }

  @Test
  public void should_pass_if_actual_contains_given_key() {
    maps.assertContainsKey(someInfo(), actual, "name");
  }

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    maps.assertContainsKey(someInfo(), null, "name");
  }

  @Test
  public void should_success_if_key_is_null() {
    maps.assertContainsKey(someInfo(), actual, null);
  }

  @Test
  public void should_fail_if_actual_does_not_contain_key() {
    AssertionInfo info = someInfo();
    String key = "power";
    try {
      maps.assertContainsKey(info, actual, key);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldContainKey(actual, key));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
