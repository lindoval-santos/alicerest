package org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.aiml;

import org.xml.sax.Attributes;

import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.Match;

public class A extends TemplateElement
{

  private String href;
	
  /*
  Constructors
  */

  public A(Attributes attributes)
  {
	this.href = attributes.getValue("href");
  }

  public A(Object... children)
  {
    super(children);
  }

  public A(String _href)
  {
    this.href = _href;
  }
  
  /*
  Methods
  */
  public boolean equals(Object compared)
  {
    if (compared == null || !(compared instanceof Get))
      return false;
    else
      return href.equals(((A) compared).href);
  }
  
  public int hashCode()
  {
    return href.hashCode();
  }

  public String process(Match match)
  {
    try
    {
      //String value = (String) match.getCallback().getContext().property("predicate." + href);
      String value = "<a href=\""+href+"\">"+(String) super.process(match)+"</a>";
      return (value != null ? value : "");
    }
    catch (NullPointerException e)
    {
      return "";
    }
  }
}