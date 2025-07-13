import jssc.SerialPort;
import jssc.SerialPortException;

class SerialPortHandle {

	SerialPort sp;
	String path;

	public SerialPortHandle(String path) {
		super();
		this.sp = new SerialPort(path);

		this.path = path;
		try {
			sp.openPort();
			sp.setParams(9600, 8, 1, 0);

			// Flush garbage data on initial open
			while (sp.getInputBufferBytesCount() > 0) {
				sp.readBytes();
			}

		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Open serial port

	}

	public String readLine() {
		StringBuilder string = new StringBuilder();
		while (true) {
			try {
				byte[] buffer = sp.readBytes(1);
				if (buffer == null || buffer.length == 0)
					continue;

				char c = (char) buffer[0];

				if (c == '\n' || c == '\r') {
					if (string.length() == 0) {
						continue; // skip empty lines
					} else {
						break; // return non-empty line
					}
				}

				string.append(c);

			} catch (SerialPortException e) {
				e.printStackTrace();
				break;
			}
		}
		return string.toString().trim();
	}

	public void printLine(String s) {
		byte byteArray[] = s.getBytes();
		try {
			sp.writeBytes(byteArray);
			sp.writeByte((byte) '\n');
		} catch (SerialPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}