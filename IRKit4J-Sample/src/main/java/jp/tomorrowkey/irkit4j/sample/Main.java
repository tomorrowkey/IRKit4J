package jp.tomorrowkey.irkit4j.sample;

import jp.tomorrowkey.irkit4j.LocalIRKit;
import jp.tomorrowkey.irkit4j.RemoteIRKit;
import jp.tomorrowkey.irkit4j.entity.Messages;
import jp.tomorrowkey.irkit4j.util.StringKeyValue;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    public static void main(String... args) {
        if (args.length == 0) {
            usage();
            return;
        }

        InetAddress inetAddress = getInetAddressFromArguments(args);
        if (inetAddress == null) {
            usage();
            return;
        }

        try {
            // Acquire Client Token from IRKit in the same network
            String clientToken = LocalIRKit.getKeys(inetAddress);

            // Acquire Device ID and Client Key from https://api.getirkit.com/
            StringKeyValue authenticationToken = RemoteIRKit.postKeys(clientToken);
            String deviceId = authenticationToken.get("deviceid");
            String clientKey = authenticationToken.get("clientkey");

            // Record a signal of your IRKit.
            Messages messages = RemoteIRKit.getMessages(clientKey, true);

            if (messages == null) {
                System.out.println("Can not record any signals.");
                return;
            }
            // Command send the signal to your IRKit via internet.
            RemoteIRKit.postMessage(clientKey, deviceId, messages.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void usage() {
        System.out.println("Usage");
        System.out.println("----------------------");
        System.out.println("Set IP Address of IRKit as argument like this '192.168.0.56'.");
        System.out.println("Host name is able to be set as well.");
    }

    private static InetAddress getInetAddressFromArguments(String... arguments) {
        if (arguments == null || arguments.length < 1)
            return null;

        String inputInetAddress = arguments[0];
        try {
            return InetAddress.getByName(inputInetAddress);
        } catch (UnknownHostException e) {
            System.out.println("Failed to resolve ip address. your input is " + inputInetAddress);
            return null;
        }
    }

}
