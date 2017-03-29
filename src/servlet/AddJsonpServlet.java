package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/addjsonp"}, description = "Add servlet with JSONP")
public class AddJsonpServlet extends HttpServlet
{
    private static final long serialVersionUID = -8485701202623343322L;
	
	private static final String TEXT_CONTENT_TYPE = "text/plain";
//	private static final String XML_CONTENT_TYPE  = "text/xml";
//	private static final String HTML_CONTENT_TYPE = "text/html";
//	private static final String JSON_CONTENT_TYPE = "application/json";	 // Official : IETF RFC-4627  

//	private final static int TEXT = 1 ;
//	private final static int XML =  2 ;
//	private final static int HTML = 3 ;
//	private final static int JSON = 4 ;
	
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
        try
        {
        	String f = request.getParameter("f");
        	if ( f == null ) {
        		f = "processJSONP";
        	}
        	String s1 = request.getParameter("p1");
        	String s2 = request.getParameter("p2");
        	
        	double d1 = paramValue(s1, "p1" );
        	double d2 = paramValue(s2, "p2" );
        	double result = d1 + d2 ;
        	System.out.println( " result = " + result + " ( " + d1 + " + " + d2 + " ) ");
            printResponse(response, result, f);
        } catch (Exception e)
        {
        	printError(response, e.getMessage());
        }
    }
    //-----------------------------------------------------------------------------
    private void printResponse(HttpServletResponse response, double result, String f ) throws ServletException, IOException
    {
    	int random = (int) ( Math.random() * 1000 ) ;
	    PrintWriter out = response.getWriter();
	    response.setContentType(TEXT_CONTENT_TYPE);
	    //response.setHeader(HEADER_TEXT_TYPE, JSON_TEXT_TYPE );
	    out.print(f + "(");
	    out.print("{");
	    out.print("\"random\" : \"" + random + "\" , ");
	    out.print("\"result\" : \"" + result + "\"  ");
	    out.print("}");
	    out.print(")");
    }
    //-----------------------------------------------------------------------------
    private void printError(HttpServletResponse response, String msg ) throws ServletException, IOException
    {
	    PrintWriter out = response.getWriter();
	    response.setContentType(TEXT_CONTENT_TYPE);
	    out.println("error : " + msg );
    }
}