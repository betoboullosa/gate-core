/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 29/10/2001
 *
 *  $Id: LanguageAnalyserPersistence.java 18176 2014-07-11 15:45:13Z johann_p $
 *
 */
package gate.util.persistence;

import gate.*;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
/**
 * Provides a persistent equivalent for {@link LanguageAnalyser}s.
 * Adds handling of corpus and document members for PRPersistence.
 */
public class LanguageAnalyserPersistence extends PRPersistence {
  /**
   * Populates this Persistence with the data that needs to be stored from the
   * original source object.
   */
  @Override
  public void extractDataFromSource(Object source)throws PersistenceException{
    if(! (source instanceof LanguageAnalyser)){
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                LanguageAnalyser.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + LanguageAnalyser.class.getName());
    }

    super.extractDataFromSource(source);

    LanguageAnalyser la = (LanguageAnalyser)source;
    document = PersistenceManager.getPersistentRepresentation(la.getDocument());
    corpus = PersistenceManager.getPersistentRepresentation(la.getCorpus());
  }

  /**
   * Creates a new object from the data contained. This new object is supposed
   * to be a copy for the original object used as source for data extraction.
   */
  @Override
  public Object createObject()throws PersistenceException,
                                     ResourceInstantiationException{
    LanguageAnalyser la = (LanguageAnalyser)super.createObject();
    la.setCorpus((Corpus)PersistenceManager.getTransientRepresentation(corpus));
    la.setDocument((Document)PersistenceManager.getTransientRepresentation(
            document,containingControllerName,initParamOverrides));
    return la;
  }


  protected Object corpus;
  protected Object document;
  static final long serialVersionUID = -4632241679877556163L;
}