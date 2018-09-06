package org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.aiml;

import org.xml.sax.Attributes;

import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.Match;

public class A extends TemplateElement
{

  private String href;
  private boolean isPesquisa;
	
  /*
  Constructors
  */

  public A(Attributes attributes)
  {
	this.href = attributes.getValue("href");
	try{
	 this.isPesquisa = Boolean.valueOf(attributes.getValue("pesquisar"));
	}catch(Exception e){this.isPesquisa = false;};
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
      if (href == null || "".equals(href))
    	return "";
      String texto = (String) super.process(match);
      String value = "<a target=\"_blank\" href=\"" + href;
      if(isPesquisa)
    	value = value + texto + "\">"+texto+"</a>";
      else
    	value = value + "\">"+texto+"</a>";
      return (value != null ? value : "");
    }
    catch (NullPointerException e)
    {
      return "";
    }
  }
}