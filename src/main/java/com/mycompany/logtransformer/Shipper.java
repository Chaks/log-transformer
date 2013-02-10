/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.logtransformer;

import java.util.concurrent.TimeUnit;
import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author dchakr
 */
public class Shipper {

  private static String inputLogDirectory;
  private static String inputLogFile;
  private static String outputLogDirectory;
  private static String outputLogFile;
  private static String logQueue;

  /**
   *
   * @param args
   * @throws Exception
   */
  public static void main(String args[]) throws Exception {
    inputLogDirectory = args[0];
    inputLogFile = args[1];
    outputLogDirectory = args[2];
    outputLogFile = args[3];
    logQueue = args[4];

    CamelContext camelContext = new DefaultCamelContext();
    camelContext.addComponent("activemq", activeMQComponent("tcp://ubuntu:61616?trace=true"));
    camelContext.addRoutes(new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("stream:file?fileName=" + inputLogDirectory + inputLogFile + "&scanStream=true&scanStreamDelay=1")
                .to("activemq:" + logQueue);
        from("activemq:" + logQueue).process(new FieldTransformer())
                .to("stream:file?fileName=" + outputLogDirectory + outputLogFile + "&scanStream=true&scanStreamDelay=1");
      }
    });
    camelContext.start();
    TimeUnit.DAYS.sleep(365);
  }
}
