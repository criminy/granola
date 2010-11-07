package granola.template.tokens.syntax.rules;

import static org.junit.Assert.*;

import java.io.OutputStreamWriter;
import java.io.Writer;

import granola.template.commands.For;
import granola.template.commands.If;
import granola.template.context.Context;
import granola.template.tokens.syntax.impl.AutoboxingRunner;
import granola.template.tokens.syntax.objects.Children;
import granola.template.tokens.syntax.objects.Value;
import granola.template.tokens.syntax.objects.ValueList;
import granola.template.util.Runnable1F;

import org.junit.Test;


public class SyntaxRulesTest {

public class CountingChildren implements Children
{
	
	
	public String node;
	
   public void nullTest() {}


	public void exec(Writer os, String node, Runnable1F<Context> runnable) {
		this.node = node;
	}
}



   public class IfCase
   {
	//These should be stateless
	If instance = new If();
	SyntaxRules r = instance.getSyntax();
	
	AutoboxingRunner runner = new AutoboxingRunner();
	Context ctx = new Context();
	CountingChildren ch = new CountingChildren();		
	Value v;
	Writer wr = new OutputStreamWriter(System.out);

   public IfCase() {

   }	

	/*public IfCase(String x) {
		v = r.parse(x);
	}*/
	
	public void run(String x)
	{
      v = r.parse(x);
		runner.invokeMethod(wr, ctx, instance, v,ch);
	}

   public void nullTest() {}	

}


	
	@Test
	public void testIf()
	{
		{//1
			IfCase tc = new IfCase();					
			tc.ctx.addObject("y",2);
			tc.ctx.addObject("x",4);			
			tc.run("x == y");
			assertEquals(tc.ch.node,"else");
		}
		
		{//2
			IfCase tc = new IfCase();					
			tc.ctx.addObject("y",2);
			tc.ctx.addObject("x",2);			
			tc.run("x == y");	
			assertEquals(tc.ch.node,"main");
		}
		
		{//3
			IfCase tc = new IfCase();
			tc.ctx.addObject("x",4);			
			tc.run("x");
			assertEquals(tc.ch.node,"main");
		}
		
		{//4
			IfCase tc = new IfCase();		
			tc.run("x");		
			assertEquals(tc.ch.node,"else");
		}
		
		{//5
			IfCase tc = new IfCase();
			tc.ctx.addObject("x",4);
			tc.ctx.addObject("y",6);		
			tc.run("x != y");			
			assertEquals(tc.ch.node,"main");
		}
		
		{//6
			IfCase tc = new IfCase();
			tc.ctx.addObject("x",4);
			tc.ctx.addObject("y",4);
			tc.run("x != y");	
			assertEquals(tc.ch.node,"else");
		}
		
		{//7
			IfCase tc = new IfCase();
			tc.ctx.addObject("x",new Integer(4));
			tc.ctx.addObject("y",4);
			tc.run("x != y");
			assertEquals(tc.ch.node,"else");
		}
	}
	
	
	
		
		
	@Test
	public void forTest()
	{
		For forObject = new For();
		SyntaxRules r = forObject.getSyntax();
		
		Value s3 = (Value) r.parse("5");
		
		System.out.println(s3.getValue());
		
		ValueList s = (ValueList) r.parse("x in y");
		
		for(Value rx : s.getList())
		{
			System.out.println("> " + rx.getValue());
		}
		
		
		
		System.out.println( r.parse("x in [0..4]"));
		
		/*
		for(SyntaxRules rx : s.rules)
		{
			System.out.println("> " + rx);
			if(rx instanceof Value && ((Value)rx).getValue() instanceof List){
				Value v = (Value)rx;
				List<Integer> l = (List) v.getValue();
				for(int i : l)
				{
					System.out.println(i);
				}
			}
		}
		*/
		
	}
	
	
}


