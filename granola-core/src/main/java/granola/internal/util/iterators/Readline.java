package granola.internal.util.iterators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

class Readline implements Iterable<String> {
	BufferedReader reader;

	
	public Readline(Reader er) {
		this.reader = new BufferedReader(er);
	}
	

	
	public Readline(InputStream is) {
		this.reader = new BufferedReader(new InputStreamReader(is));
	}
	
	
	public class ReadlineIterator implements Iterator<String>
	{
		
		BufferedReader reader;
		public ReadlineIterator(BufferedReader re) {
			reader = re;
		}
		
		String line;
		boolean readNext = true;
		
		@Override
		public boolean hasNext() {
			try {
				line = reader.readLine();
				readNext = false;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if(line == null)
				return false;
			return true;
		}
		
		@Override
		public String next() {
			if(readNext)
			{
				try {
					line = reader.readLine();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}			
			}
			readNext = true;
			return line;
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	
	
	@Override
	public Iterator<String> iterator() {
		return new ReadlineIterator(this.reader);
	}
	
	
}
