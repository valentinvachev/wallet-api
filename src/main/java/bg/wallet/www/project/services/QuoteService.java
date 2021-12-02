package bg.wallet.www.project.services;

import bg.wallet.www.project.models.view.QuoteViewModel;

import java.util.List;

public interface QuoteService {
   List<QuoteViewModel> findAll();
    QuoteViewModel findMain();
    void seedQuotes();
    void makeMain();
}
