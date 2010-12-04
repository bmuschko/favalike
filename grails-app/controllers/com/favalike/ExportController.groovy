package com.favalike

class ExportController {
    def exportService

    def html = {
		log.info "Exporting bookmarks to HTML"
		def html = exportService.exportToHtml()
        response.setHeader("Content-disposition", "attachment;filename=bookmarks.html")
        render(contentType: "application/octet-stream", text: html)
	}

    def xml = {
        log.info "Exporting bookmarks to XML"
        def xml = exportService.exportToXml()
        response.setHeader("Content-disposition", "attachment;filename=bookmarks.xml")
        render(contentType: "text/xml", text: xml)
    }
}
