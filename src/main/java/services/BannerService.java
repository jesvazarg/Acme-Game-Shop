
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BannerRepository		bannerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors------------------------------------------------------------
	public BannerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Banner findOne(final int bannerId) {
		Banner result;

		result = this.bannerRepository.findOne(bannerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Banner> findAll() {
		Collection<Banner> result;

		result = this.bannerRepository.findAll();

		return result;
	}

	public Banner create() {
		Banner result;

		Assert.notNull(this.administratorService.findByPrincipal());

		result = new Banner();

		return result;
	}

	public Banner save(final Banner banner) {
		Assert.notNull(banner);
		Banner result;

		Assert.notNull(this.administratorService.findByPrincipal());

		result = this.bannerRepository.save(banner);

		return result;
	}

	public void delete(final Banner banner) {
		Assert.notNull(banner);
		Assert.isTrue(banner.getId() != 0);

		Assert.notNull(this.administratorService.findByPrincipal());

		this.bannerRepository.delete(banner);
	}

	// Other business methods -------------------------------------------------

	public Banner getRandomBanner() {
		Banner result = null;
		List<Banner> banners;
		Integer index;

		banners = this.bannerRepository.findAll();
		if (banners.size() > 0) {
			index = (int) (Math.random() * banners.size());
			result = banners.get(index);
		}

		return result;

	}

}
