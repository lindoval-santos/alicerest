package org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.aiml;

import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.Match;
import org.xml.sax.Attributes;

public class Br extends TemplateElement
{
  /*
  Constructors
  */

  public Br(Attributes attributes)
  {
  }

  public Br(Object... children)
  {
    super(children);
  }

  /*
  Methods
  */

  public String process(Match match)
  {
    return "<br/>";
  }
}
