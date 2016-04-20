package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/add"}, description = "Add servlet")
public class AddServlet extends HttpServlet
{
    private static final long serialVersionUID = -8485701202623343322L;
	
	private static final String TEXT_CONTENT_TYPE = "text/plain";
	private static final String XML_CONTENT_TYPE  = "text/xml";
	private static final String HTML_CONTENT_TYPE = "text/html";
	private static final String JSON_CONTENT_TYPE = "application/json";	 // Official : IETF RFC-4627  
	//private static final String JSON_CONTENT_TYPE = "text/javascript";	 // Used by Yahoo 
	//private static final String JSON_CONTENT_TYPE = "text/plain";	 

//	private static final String HEADER_TEXT_TYPE = "Text-Type";	 
//	private static final String JSON_TEXT_TYPE = "json";	 

	private final static int TEXT = 1 ;
	private final static int XML =  2 ;
	private final static int HTML = 3 ;
	private final static int JSON = 4 ;
	
    //-----------------------------------------------------------------------------
    //--- Requête HTTP GET
    //-----------------------------------------------------------------------------
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("---> ADD : http GET ..." );
        process(request, response);
    }

    //-----------------------------------------------------------------------------
    //--- Requête HTTP POST
    //-----------------------------------------------------------------------------
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("---> ADD : http POST ..." );
        process(request, response);
    }

    //-----------------------------------------------------------------------------
    private int getType(HttpServletRequest request)  
    {
    	String sType = null ;
        String sHeaderType = request.getHeader("response-type") ;
        if ( sHeaderType != null )
        {
        	sType =  sHeaderType ;
        }
        else
        {
        	String sParamType = request.getParameter("type") ;
            if ( sParamType != null )
            {
            	sType = sParamType ;
            }
        }
        if ( sType == null ) { sType = "text" ; }
        System.out.println(" response-type = '" + sType + "'");
        if ( sType.equalsIgnoreCase("text") ) return TEXT ;
        if ( sType.equalsIgnoreCase("xml") ) return XML ;
        if ( sType.equalsIgnoreCase("html") ) return HTML ;
        if ( sType.equalsIgnoreCase("json") ) return JSON ;
        return TEXT ; // default type
    }
    
    //-----------------------------------------------------------------------------
    private double paramValue(String s, String name ) throws Exception 
    {
    	if ( s == null )
    	{
    		throw new Exception ( "Parameter " + name + " is null" );
    	}
    	double d = 0 ;
    	try {
			d = Double.parseDouble(s);
		} catch (RuntimeException e) {
    		throw new Exception ( "Parameter " + name + " invalid value " + s );
		}
		return d ;
    }
    //-----------------------------------------------------------------------------
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //--- No cache
//        response.setHeader ("Pragma", "no-cache");
//        response.setHeader ("Cache-Control", "no-cache");
//        response.setDateHeader ("Expires", 0);
        
        //--- Response type : "text", "xml", "html", "json"
        int type = getType(request);
        try
        {
        	String s1 = request.getParameter("p1");
        	String s2 = request.getParameter("p2");
        	
        	double d1 = paramValue(s1, "p1" );
        	double d2 = paramValue(s2, "p2" );
        	double result = d1 + d2 ;
        	System.out.println( " result = " + result + " ( " + d1 + " + " + d2 + " ) ");
            printResponse(response, result, type );
        } catch (Exception e)
        {
        	printError(response, e.getMessage(), type);
        }
        
    }
    //-----------------------------------------------------------------------------
    private void printResponse(HttpServletResponse response, double result, int type ) throws ServletException, IOException
    {
    	int random = (int) ( Math.random() * 1000 ) ;
	    PrintWriter out = response.getWriter();
	    switch (type)
	    {
	    case TEXT :
		    response.setContentType(TEXT_CONTENT_TYPE);
		    out.println("random = " + random );
		    out.println("result = " + result );
	    	break ;
	    	
	    case XML :
		    response.setContentType(XML_CONTENT_TYPE);
		    out.println("<response>");
		    out.println("<random>" + random + "</random>");
		    out.println("<result>" + result + "</result>");
		    out.println("</response>");
	    	break ;
	    	
	    case HTML :
		    response.setContentType(HTML_CONTENT_TYPE);
		    out.println("<div style='background-color: Yellow;' >");
		    out.println("<span >random = " + random + "</span><br>");
		    out.println("<span style='font-weight: bold; background-color: orange;'>result = " + result + "</span>");
		    out.println("</div>");
	    	break ;
	    	
	    case JSON :
		    response.setContentType(JSON_CONTENT_TYPE);
		    //response.setHeader(HEADER_TEXT_TYPE, JSON_TEXT_TYPE );
		    out.println("{");
		    out.println("\"random\" : \"" + random + "\" , ");
		    out.println("\"result\" : \"" + result + "\"  ");
		    out.println("}");
	    	break ;
	    }
    }
    //-----------------------------------------------------------------------------
    private void printError(HttpServletResponse response, String msg, int type  ) throws ServletException, IOException
    {
	    PrintWriter out = response.getWriter();
	    switch (type)
	    {
	    case TEXT :
		    response.setContentType(TEXT_CONTENT_TYPE);
		    out.println("error : " + msg );
	    	break ;
	    	
	    case XML :
		    response.setContentType(XML_CONTENT_TYPE);
		    out.println("<response>");
		    out.println("<error>" + msg + "</error>");
		    out.println("</response>");
	    	break ;
	    	
	    case HTML :
		    response.setContentType(HTML_CONTENT_TYPE);
		    out.println("<div style='background-color: Orange;' >");
		    out.println("<span >ERROR : " + msg + "</span><br>");
		    out.println("</div>");
	    	break ;
	    	
	    case JSON :
		    response.setContentType(JSON_CONTENT_TYPE);
		    //response.setHeader(HEADER_TEXT_TYPE, JSON_TEXT_TYPE );
		    out.println("{");
		    out.println("\"error\" : \"" + msg + "\"");
		    out.println("}");
	    	break ;
	    	
	    }
    }
}