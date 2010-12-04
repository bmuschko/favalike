package com.favalike;

class IndexController {
    def indexService

	def index = {
		log.info "Loading index page"
		def user = indexService.getUser()
		render view: '/index', model: [user: user]
	}
}