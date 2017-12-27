package com.lukeyboy1111.fitba.apis.exceptions;

/**
 * 
 */
public class TeamNotFoundException extends Exception
{
    private static final long serialVersionUID = -3386746977962872296L;
    
    private String teamId;

    public TeamNotFoundException(String tid)
    {
        super();
        this.teamId = tid;
    }
    
    public String getTeamId()
    {
        return teamId;
    }
}
