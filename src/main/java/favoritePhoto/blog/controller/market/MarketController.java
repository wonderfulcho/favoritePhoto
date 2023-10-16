package favoritePhoto.blog.controller.market;

import favoritePhoto.blog.model.market.MarketDTO;
import favoritePhoto.blog.service.market.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/market")
@Controller
public class MarketController {

    private final MarketService marketService;

    @Autowired
    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping("/marketMain")
    public String crawlingPage(Model model,
                               @PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MarketDTO> page = marketService.getCrawledDataByPage(pageable);
        model.addAttribute("page", page);
        model.addAttribute("previous", page.hasPrevious() ? page.previousPageable().getPageNumber() : -1);
        model.addAttribute("next", page.hasNext() ? page.nextPageable().getPageNumber() : page.getTotalPages());
        model.addAttribute("startPage", page.getNumber() / 10 * 10);
        model.addAttribute("endPage", Math.min(page.getNumber() / 10 * 10 + 9, page.getTotalPages() - 1));

        return "market/marketMain";
    }
}
