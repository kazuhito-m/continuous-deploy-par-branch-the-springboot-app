package github.com.kazuhito_m.cdparbranchapp.sample;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleTest {
    private static final Logger logger = LoggerFactory.getLogger(SampleTest.class);

    @Test
    public void 申し訳程度のテスト_CIで確認するため() {
        logger.info("ここのテストは通りましたよ!");
    }
}
