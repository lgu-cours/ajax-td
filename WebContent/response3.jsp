<%@ page language="java" pageEncoding="iso-8859-1" contentType="text/html; charset=iso-8859-1" %>
<HTML>
<HEAD>
 <TITLE>Response 3</TITLE>
</HEAD>
<body>
<%
 //--- No cache
 response.setHeader ("Pragma", "no-cache");
 response.setHeader ("Cache-Control", "no-cache");
 response.setDateHeader ("Expires", 0);
%>

 <H2>Response 3</H2>
 <h2>Response start ( date = <%= new java.util.Date() %> ) </h2>
 
<%
  //--- Wait for N seconds
  int iDurationInSec = 5 ;
  int iDurationInMiliSec = iDurationInSec * 1000 ;
  try
  {
    Thread.sleep(iDurationInMiliSec);
  } catch (InterruptedException e)
  {
  }
%>

<h2>Response end ( date = <%= new java.util.Date() %> ) </h2>
 
<body>
</HTML>
