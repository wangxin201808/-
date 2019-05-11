package com.conner.shop.commons.constant;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092500595989";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCFigWAjH3PW7pHikMgCMVva85dqqQLBKVKwXYTIQxItx0b0SUkwKthEMl6OmdtPhHgajixb9DfNzV62P/Yw25CrYnP96/2qH9ets27njr14Ow5i4WaYsdGZjiMcwNG74FqSzx+9HMW0T9Gh57suszOHyDpK3NZuVpLS4IHJOEVO298WBwnyASmCrSjQLbnVsq6LcBxQX+PqxdH2XGgyVrlYZJKlTziH+hdcqSCNgNbMXOo90jgdMklWhl44I8QUJLGjgbirE2LKaCxTsjs4RU3lHtm9AOclU32UUn71nwmw0gWp202haVPzTPEYViWTKudu1O89M0Db9LCcM5B4wpdAgMBAAECggEAadmZuueh4fenlIV357CML2GY1o4oMcOg8byfx1Hu1sSvAhmqA5PlRK91xLj+uoz0xpnsDlm/iu/MKHBRfJSMlbXRA1Ql2rJ9I6prMUifG78KACMj59hcdOUZ9dpEwrmeL48riMZjY7bBHTrrMmJvbQMnpyuV540QkZZsnxUQhS7KilNC3ZogaEPoFiyH2mwdaamIu+r4KTt+wLAiZIWI74RJ7fohIkgEpT/ACf6jlpWP3WlXCB9ugyyjCwUxRMP4YfiZq28mykKF0Kp730/q0ocTzId1nX8IiOR4RG4c4pfOT5/LmIA5q1QX2cneuEeXkt95xtCNMxSfDLTsjDTv0QKBgQDZtJqpCj+WJ5sUrzNRByjeXmhCXibZwybQwrc/LxcIbByoC0PIgobI+J2l6NjNZ8GcnK02+ng97LKiZe+grizmBLeEDjeyrTrpUlkxCCLomENw99vh7V4Q1/PSzQVUfJNtelEPT6K9S/M7nJypAbD/SuaQH8Klk1utmgJHZZz7lwKBgQCdB1yPKUvOqDRKNysxSgh4o+IaPuzlSlIMn/yhZlzN4q7cKWr3YKMfNdEf517/KkotkbHAwaoxmP2xvRgY+KyHnDPtlwxqfJaLR3YNzU5qlbonS1ce4XBVnvsn76XhbWlPXifAcNoGc929B5wJhA7uFyXWIc6RoNZWZFs+BNp4KwKBgQCOFGs3VQPoqBJy7mCMSiLm9TAP2Eh8p/smFV3oc4ARtBUDqs5jBqSWdoLUa2FKtKNL5Yi6L77XkymGCQbZk7Kjr4JhQbIh/yJKcb7Z3BiEOhMRoAE1smdIkNeExN7so5zHvyM13eA0sGOejPhE6GCuTCCrAL9UthYH91HzbKKGIwKBgFd+VpBeDiMjjZ8chjB8c6PkjILbNNu9QWJPck/r3SHKgGujmDol2j31moQQQtoiEhq55zTjs5TXDeQO3HJtfJRC8JwdYYmjya3pL1oZXuv8aTCjxfMq4ZCTeJWT7fVhJSBX8gZD2vdlb9N3ffW/YyI+O5LIW3TBDSwt1rkDs0bpAoGALjs/GX8vOcp8sFPKDRJSZ2q8UGDSAtN68R7SKVO87slAquFUvQwm40FnQxwzsRZfJ2ggEAr8qAWvSoP4Z9I7IVFqWWc49Xf1SBrvQJGKfvzG/74hNyvqXdjZXoIO2AvD8URNQX8xnEcJHusgefJamAVgK97WubU9XfcswATXkpI=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyUNhIt/ppjL88UBwoMWMQLLC87zWcfbnxoTwI7Ev/jQr5y3/Sl5cRIYiNMa2gdkHBhH9Ctx1o9jLYW+1qc/JCqyHvOFqma6pTD04O/FfgujrW+z5PSfuUMAPHc0WAZJQhDzPSW4cayURHZCDfaOOoCwtFqQngkJt8+JXD4foMqSAmzlwWKcwNuTZ6T6/4HrHyksCoR1NVFA7Pfkl2/7n2T24UFNKJiUOfO2SeRGY/DKZaW19NvlztDNXy9HyYioLqjEpJDoJiiNrZSlqnHX8YqlgnA4sfgtbsd3mWS2UlXkbyg+CV8JM4GZvElaRzurqppm0iBgtqDO9YzPvJjVKawIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://66y26a.natappfree.cc/shop_web_api_war_exploded/notifyUrl";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://66y26a.natappfree.cc/shop_web_api_war_exploded/returnUrl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";

}
