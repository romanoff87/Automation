package home.Utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Utils {

	private final static int WAKE_ON_LAN_PORT = 9;
	private final static String BROADCAST_IP = "255.255.255.255";



	public static boolean WakeOnLan(String mac_adress) {
		boolean is_packet_send = false;
		try {
			byte[] mac_bytes = MacAdressToByteArray(mac_adress);
			byte[] magik_packet = contructMagikPacket(mac_bytes);
			sendMagikPacket(magik_packet);
			is_packet_send = true;
		} catch (IllegalArgumentException e) {
			is_packet_send = false;
		} catch (Exception e) {
			is_packet_send = false;
		}
		return is_packet_send;
	}

	private static byte[] MacAdressToByteArray(String mac_adress)
			throws IllegalArgumentException {
		byte[] mac_bytes = new byte[6];
		String[] hex = mac_adress.split("(\\:|\\-)");
		if (hex.length != 6) {
			throw new IllegalArgumentException("Invalid MAC address.");
		}
		try {
			for (int i = 0; i < 6; i++) {
				mac_bytes[i] = (byte) Integer.parseInt(hex[i], 16);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Invalid hex digit in MACaddress.");
		}
		return mac_bytes;
	}

	private static byte[] contructMagikPacket(byte[] mac_adress) {
		byte[] bytes = new byte[16 * 7];
		for (int i = 0; i < 6; i++) {
			bytes[i] = (byte) 0xff;
		}
		for (int i = 6; i < bytes.length; i += mac_adress.length) {
			System.arraycopy(mac_adress, 0, bytes, i, mac_adress.length);
		}
		return bytes;
	}

	private static void sendMagikPacket(byte[] magik_packet) throws Exception {
		InetAddress broadcast_address = InetAddress.getByName(BROADCAST_IP);
		DatagramPacket wol_packet = new DatagramPacket(magik_packet,
				magik_packet.length, broadcast_address, WAKE_ON_LAN_PORT);
		DatagramSocket socket = new DatagramSocket();
		socket.send(wol_packet);
		socket.close();
	}
}