public class X_1b extends Thread    {
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
	public X_1b(int id)	{
		this.id = id;
		o       = new Object();
	}
	public void run () {
		if ( id == 0 )	{
			new X_1b(1).start();
			new X_1b(2).start();
			return;
		}
		if (id == 1) {
			s(20);
		} else {
			s(10);
		}
		synchronized ( o ) {
			System.err.println(id + " --->");
			try {
				if ( counter == 0 )	{
					counter = 1;
					o.wait();
				} else
					o.notifyAll();
			}
			catch (  InterruptedException e ) {
			}
			System.err.println(id + " <---");
		}
	}
	public static void main (String args []) {
		new X_1b(0).start();
	}
}

