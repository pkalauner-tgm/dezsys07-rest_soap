package at.kalaunermalik.dezsys07.soaclient;


import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by patrick on 16.12.2015.
 */
public class Cli {
    private static final Logger logger = LogManager.getLogger(Cli.class.getName());
    private String[] args;
    private Options options = new Options();


    public Cli(String[] args){
        this.args = args;

        options.addOption("h","help",false,"help");
        options.addOption("a","address",true,"The hostaddress of the soap server");
        options.addOption("p","port",true,"The port of the soap server");

    }

    public String parse(){
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        String url = "http://";
        try{
            cmd = parser.parse(options, args);

            if(cmd.hasOption("h"))
                printHelp();

            if(cmd.hasOption("a")){
                url += cmd.getOptionValue("a");
            }else{
                logger.log(Level.ERROR,"Missing -a");
                printHelp();
                System.exit(1);
            }

            if(cmd.hasOption("p")){
                url += ":"+cmd.getOptionValue("p");
            }else{
                logger.log(Level.ERROR,"Missing -p");
                printHelp();
                System.exit(1);
            }
        } catch (ParseException e) {
            logger.log(Level.FATAL, "Could not parse given arguments");
        }

        return url;
    }

    private void printHelp() {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("SOAP Client",options);
    }
}
