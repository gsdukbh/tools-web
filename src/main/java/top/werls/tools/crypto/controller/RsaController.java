package top.werls.tools.crypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.werls.tools.common.utils.crypto.asymmetric.RSA;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
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


    public static String[] ss = new String[]{"top", "werls"};
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

        Random r = new Random();
        var initialize = r.nextInt(1, data.length());
        var l = data.length() / initialize;
        var lister = new ArrayList<String>();
        for (int i = 0; i < data.length(); ) {
            lister.add(data.substring(i, (i + l) >= data.length() ? (data.length() - 1) : i + l));
            lister.add(ss[r.nextInt(0, 2)]);
            i += l;
        }
        StringBuilder builder = new StringBuilder();
        for (var s : lister) {
            builder.append(s);
        }
        var res = RSA.encryptToBase64(builder.toString(), key);
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
        var decryptSt = new String(by).replace(ss[0], "").replace(ss[1], "");
        return decryptSt;

    }
}
