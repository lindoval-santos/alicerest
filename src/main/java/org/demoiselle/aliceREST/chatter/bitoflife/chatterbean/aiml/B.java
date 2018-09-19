package org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.aiml;

import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.Match;
import org.xml.sax.Attributes;

public class B extends TemplateElement
{
  /*
  Constructors
  */

  public B(Attributes attributes)
  {
  }

  public B(Object... children)
  {
    super(children);
  }

  /*
  Methods
  */

  public String process(Match match)
  {
	String negrito = super.process(match);
    return "&lt;b&gt;" + negrito + "&lt;&#47;b&gt;"; //&#47;
  }
}
