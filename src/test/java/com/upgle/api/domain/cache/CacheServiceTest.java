package com.upgle.api.domain.cache;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//이거 통합 테스트가아니라 단위 테스트로 바꿔야 함
@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = {
    "test",
    "maildev"
})
@SpringBootTest
public class CacheServiceTest {

  @Autowired
  CacheService cacheService;
  final String inputKey = "key";
  final String inputValue = "value";
  final int inputTTL = 10;

  @BeforeEach
  private void setUp() {
    cacheService.setCacheString(inputKey, inputValue, inputTTL);
  }

  @Test
  public void 캐시값_조회_테스트() {
    String getValue = cacheService.getCacheString("key");
    assertThat(getValue).isEqualTo("value");
  }

  @Test
  public void 캐시값_설정_테스트() {
    //given
    String key = "key2";
    String value = "value2";
    final int inputTTL2 = 10;

    //when
    cacheService.setCacheString(key, value, inputTTL2);

    //then
    String storedValue = cacheService.getCacheString(key);

    assertThat(storedValue).isEqualTo(value);
  }

  @Test
  public void TTL_테스트_5초가주어질때_3초후_조회성() throws InterruptedException {
    //given
    String key = "key2";
    String value = "value2";
    final int inputTTL2 = 5;

    //when
    cacheService.setCacheString(key, value, inputTTL2);
    Thread.sleep(3000); // 3초뒤

    String storedValue = cacheService.getCacheString(key);

    assertThat(storedValue).isEqualTo(value);
  }

  @Test
  public void TTL_테스트_5초가주어질때_6초후_조회실패() throws InterruptedException {
    //given
    String key = "key2";
    String value = "value2";
    final int inputTTL2 = 5;

    //when
    cacheService.setCacheString(key, value, inputTTL2);
    Thread.sleep(6000); // 6초뒤

    String storedValue = cacheService.getCacheString(key);

    assertThat(storedValue).isEqualTo(null);
  }

  @Test
  public void 캐시값_삭제_테스트() {
    cacheService.deleteCacheByStringKey(inputKey);
    String value = cacheService.getCacheString(inputKey);
    assertThat(value).isEqualTo(null);
  }
}
