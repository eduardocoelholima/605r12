public class OrganizedThreads extends Thread	{
	private int 		id;
	private Object 		first;
	private Object 		second;
	static final int  	MAX = 3;
	static Object 		all[] = new Object[MAX];
	static int 		createdSoFar = 1;
	static int 		lastSeen = 0;

	static {
		for ( int index = 0; index < MAX; index ++ )
			all[index] = new Object();
	}

	public OrganizedThreads (int id, Object first, Object second) {
		this.id     	= id;
		this.first  	= first;
		this.second 	= second;
	}
	public void test () {
		if ( lastSeen + 1 != id )	{
			System.out.println("Something went wrong. LastSeen/id = " + lastSeen + "/" + id );
			System.exit(1);
		}
		lastSeen = ( lastSeen + 1 ) % MAX;
			
	}
	public void run () {
		while ( true )	{
	
			try { sleep(1000); } catch (  InterruptedException e ) { }
			synchronized ( first ) {
				synchronized ( second ) {
					second.notify();
					test();
					System.out.println(id);
					try {
						if ( createdSoFar < MAX )	{
							createdSoFar++;
							( new OrganizedThreads(createdSoFar, all[createdSoFar-1], all[createdSoFar%MAX]) ).start();
						}
					} catch ( Exception e ) { }
				}
				try {
					first.wait();
				} catch ( Exception e ) { }
			}
		}
	}
	public static void main (String args []) {
		new OrganizedThreads(1, all[0], all[1]).start();
	}
}
