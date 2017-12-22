package org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.aiml;

import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.Match;
import org.xml.sax.Attributes;

public class Settopic extends TemplateElement{
	
	  public Settopic(Attributes attributes)
	  {
	  }

	  public Settopic(Object... children)
	  {
	    super(children);
	  }

	  /*
	  Methods
	  */

	  public String process(Match match)
	  {
	    return "";
	  }
}
