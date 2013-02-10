/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.logtransformer;

import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author dchakr
 */
public class FieldTransformer implements Processor {

  private static FieldMapper fieldMapper = new DefaultFieldMapper();

  /**
   *
   * @param exchng
   * @throws Exception
   */
  public void process(Exchange exchng) throws Exception {
    StringBuilder finalLogMessage = new StringBuilder();
    String logMessage = exchng.getIn().getBody(String.class);

    if (logMessage.trim().startsWith(fieldMapper.getStartIdentifier())) {
      int noOfPositions = fieldMapper.getFieldPositions().size();
      int splitLimit = fieldMapper.getFieldPositions().get(noOfPositions - 1);
      String[] logFields = logMessage.split("\\" + Character.toString(fieldMapper.getDelimiter()), splitLimit + 1);

      List<Integer> fieldPositions = fieldMapper.getFieldPositions();
      for (Integer pos : fieldPositions) {
        finalLogMessage.append(
                fieldMapper.getFieldNames().get(pos)).append("=").append(logFields[pos]).append(" ");
      }
    }

    exchng.getIn().setBody(finalLogMessage.toString());
  }
}
