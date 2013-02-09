/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.logtransformer;

import java.util.List;
import java.util.Map;

/**
 *
 * @author dchakr
 */
public abstract class FieldMapper {

  abstract String getStartIdentifier();

  abstract char getDelimiter();

  abstract List<Integer> getFieldPositions();

  abstract Map<Integer, String> getFieldNames();
}