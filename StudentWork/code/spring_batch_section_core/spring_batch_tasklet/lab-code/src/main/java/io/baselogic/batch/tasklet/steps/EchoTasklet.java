package io.baselogic.batch.tasklet.steps;

import org.springframework.batch.core.step.tasklet.Tasklet;


@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class EchoTasklet implements Tasklet {

    private String message;

    //---------------------------------------------------------------------------//

    public EchoTasklet(String message){
        this.message = message;
    }


    //---------------------------------------------------------------------------//
    // Lab: Create Tasklet execute method


} // The End...
