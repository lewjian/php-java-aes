
    public static String encrypt(String content,String key) {
        String resultEncode = "";
        try {
            //[1].利用KeyGenerator构造密钥生成器，指定为AES算法，不区分大小写
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            //[2].根据encryptEncodeRules规则初始化密钥生成器，生成一个128位的随机源,根据传入的字节数组，实现随机数算法
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            keyGenerator.init(128, random);
            //[3].产生原始对称密钥
            SecretKey originalKey = keyGenerator.generateKey();
            //[4].获得原始对称密钥的字节数组
            byte[] rawByte = originalKey.getEncoded();
            //[5].根据字节数组生成AES密钥
            SecretKey secretKey = new SecretKeySpec(rawByte, "AES");
            //[6].根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //[7].初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //[8].获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = content.getBytes("utf-8");
            //[9].根据密码器的初始化方式--加密：将数据加密
            byte[] bytesAes = cipher.doFinal(byteEncode);
            //[10].将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            resultEncode = new String(new BASE64Encoder().encode(bytesAes));
            //[11].将字符串返回
            return resultEncode;
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        } catch (NoSuchPaddingException exception) {
            exception.printStackTrace();
        } catch (InvalidKeyException exception) {
            exception.printStackTrace();
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        } catch (IllegalBlockSizeException exception) {
            exception.printStackTrace();
        } catch (BadPaddingException exception) {
            exception.printStackTrace();
        }
        return resultEncode;
    }