package org.demoiselle.aliceREST.business;

import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@BusinessController
public class BookmarkBC {

	@Startup
	@Transactional
	public void load() {

	}

	public void find(String filter) {
	}
}
