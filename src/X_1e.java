public class X_1e extends Thread    {
	static Object o = new Object();
	static int counter = 0;
	int id;
	String s(int ms) {
		try {
			sleep(ms);
			return "";
		} catch (Exception e) {
			return "";
		}
	}
	public X_1e(int id)	{
		this.id = id;
		o       = new Object();
	}
	public void run () {
		if ( id == 0 )	{
			new X_1e(1).start();
			s(10);
			new X_1e(2).start();
			return;
		}

		synchronized ( o ) {
			Object monitor = o;
			if (id == 2) {
				s(0);
			} else {
				s(10);
			}
			System.err.println(id + " --->");
			try {
				if ( counter == 0 )	{
					counter = 1;
					monitor.wait();
				} else
					monitor.notifyAll();
			}
			catch (  InterruptedException e ) {
			}
			System.err.println(id + " <---");
		}
	}
	public static void main (String args []) {
		new X_1e(0).start();
	}
}

