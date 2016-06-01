package cn.alpha.intell.factory.check.utils;

public class UploadPolicy {
    public int expire;
    public String policy;
    public String accessid;
    public String signature;
    public String host;
    public String filename;
    public String callback;
    public String dir;

    public UploadPolicy() {
        super();
    }

    public UploadPolicy(int expire, String policy, String accessid,
                        String signature, String host, String filename, String callback,
                        String dir) {
        super();
        this.expire = expire;
        this.policy = policy;
        this.accessid = accessid;
        this.signature = signature;
        this.host = host;
        this.filename = filename;
        this.callback = callback;
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "expire:" + expire + ";\n" + "policy:" + policy + ";\n" + "accessid:" + accessid
                + ";\n" + "signature:" + signature + ";\n" + "host:" + host + ";\n" + "filename:"
                + filename + ";\n" + "callback:" + callback + ";\n" + "dir:" + dir;
    }
}
