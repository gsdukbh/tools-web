package top.werls.tools.crypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.werls.tools.common.utils.captcha.CodeGenerator;
import top.werls.tools.common.utils.captcha.RandomGeneratorCode;
import top.werls.tools.common.utils.crypto.asymmetric.RSA;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jiawei Lee
 * @version TODO
 * @date created 2022/7/12
 * @since on
 */
@Controller
@RequestMapping("/public/rsa")
public class RsaController {


    public static int length = 7;

    @GetMapping("/getkeys")
    @ResponseBody
    public Map<String, String> getKeys(Integer length) throws Exception {
        if (length == null) {
            return null;
        }
        KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
        key.initialize(length);
        KeyPair keyPair = key.generateKeyPair();

        PublicKey keySpec1 = keyPair.getPublic();
        PrivateKey key1 = keyPair.getPrivate();
        var s = Base64.getEncoder().encodeToString(keySpec1.getEncoded());
        var p = Base64.getEncoder().encodeToString(key1.getEncoded());
        Map<String, String> res = new ConcurrentHashMap<>();
        res.put("Public", s);
        res.put("Private", p);
        return res;
    }

    @PostMapping("/encrypt")
    @ResponseBody
    public String encrypt(String privateKey, String data) throws Exception {
        if (data == null || data.length() == 0 || privateKey == null || privateKey.length() == 0) {
            return "";
        }
        byte[] keyBytes = Base64.getDecoder().decode(privateKey.strip());
        PKCS8EncodedKeySpec keySpec1 = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(keySpec1);

        data = data.strip();
        CodeGenerator codeCount = new RandomGeneratorCode(4);
        data = codeCount.generate() + data + codeCount.generate();
        data = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
        var res = RSA.encryptToBase64(data, key);
        return res;
    }

    @PostMapping("/decrypt")
    @ResponseBody
    public String decrypt(String publicKey, String data) throws Exception {
        if (data == null || data.length() == 0 || publicKey == null || publicKey.length() == 0) {
            return "";
        }
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.strip());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        data = data.strip();
        var by = RSA.decryptByBase64(data, key);
        var decryptSt = new String(by);
        decryptSt = new String(Base64.getDecoder().decode(decryptSt));
        decryptSt = decryptSt.substring(4, decryptSt.length() - 4);

        return decryptSt;

    }
}
