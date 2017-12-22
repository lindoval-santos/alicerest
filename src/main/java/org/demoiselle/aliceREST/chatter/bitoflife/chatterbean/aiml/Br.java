package org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.aiml;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import org.xml.sax.Attributes;

import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.AliceBot;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.Context;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.Match;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.Graphmaster;

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
    return "";
  }
}
