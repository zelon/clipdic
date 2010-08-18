import pygtk
import sgmllib
import getDic
pygtk.require('2.0')
import gtk, gobject


class ExtrTextParser(sgmllib.SGMLParser):
		def __init__(self, verbose=0):
				sgmllib.SGMLParser.__init__(self, verbose)

				self.data = ''

		def handle_data(self, data):
				self.data += data

		def getresult(self):
				return self.data

class ClipboardInfo:
    pass

class ClipboardExample:
    # update button label and tooltips
    def update_buttons(self):
        for i in range(len(self.clipboard_history)):
            info = self.clipboard_history[i]
            if info:
                button = self.buttons[i]
                if info.text:
                    button.set_label(' '.join(info.text[:16].split('\n')))
                    print("here")

        return

    # singal handler called when clipboard returns target data
    def clipboard_targets_received(self, clipboard, targets, info):
        if targets:
            # have to remove dups since Netscape is broken
            targ = {}
            for t in targets:
                targ[str(t)] = 0
            targ = targ.keys()
            targ.sort()
            info.targets = '\n'.join(targ)
        else:
            info.targets = None
            print 'No targets for:', info.text
        self.update_buttons()
        
        return

    # signal handler called when the clipboard returns text data
    def clipboard_text_received(self, clipboard, text, data):
        if not text or text == '':
            return
           
        if self.lastWord != text:

			print("new word : " + text)
			self.lastWord = text
			self.inputbox.set_text(text)

			cbi = ClipboardInfo()
			cbi.text = text
			# prepend and remove duplicate
			history = [info for info in self.clipboard_history
					if info and info.text<>text]
			self.clipboard_history = ([cbi] + history)[:self.num_buttons]
			self.clipboard.request_targets(self.clipboard_targets_received, cbi)
			self.searchDic()

		
        return

    # display the clipboard history text associated with the button
    def clicked_cb(self, button):
        self.inputbox.set_text(button.get_label())
        self.searchDic()
        return
        i = self.buttons.index(button)
        if self.clipboard_history[i]:
            #self.textbuffer.set_text(self.clipboard_history[i].text)
            self.inputbox.set_text(self.clipboard_history[i].text)
            self.searchDic()
        else:
            self.textbuffer.set_text('')
        return

    def onTimer(self):
        self.clipboard.request_text(self.clipboard_text_received)
    	return True

    # clicked search button
    def clicked_search(self, button):
    	self.searchDic()
    	return
    
    def searchDic(self):
		word = self.inputbox.get_text()
		print("search in web : " + word)
		result = getDic.testmain(word)
		parser = ExtrTextParser()
		parser.feed(result)
		parser.close()
		result = parser.getresult()
		self.textbuffer.set_text(result)
		self.addHistoryToButton(word)
		return

    def onEnterKey(self, data):
        self.searchDic()
        return

    def addHistoryToButton(self, word):
        print("addHistoryToButton : " + word)
        found = False
        for i in range(self.num_buttons):
            if self.buttons[i].get_label() == word:
                return

        for i in range(self.num_buttons):
            if i == 0:
			    continue

            j = (self.num_buttons - i)
            self.buttons[j].set_label(self.buttons[j-1].get_label())
        self.buttons[0].set_label(word)
        return

    def __init__(self):
        self.num_buttons = 10
        self.buttons = self.num_buttons * [None]
        self.clipboard_history = self.num_buttons * [None]
        self.window = gtk.Window()
        self.window.set_title("ClipDicPy")
        self.window.connect("destroy", lambda w: gtk.main_quit())
        self.window.set_border_width(0)
        
        upperFrame = gtk.HBox()
        upperFrame.show()
        
        lowerFrame = gtk.HBox()
        lowerFrame.show()
        
        vbbox = gtk.VButtonBox()
        vbbox.show()
        vbbox.set_layout(gtk.BUTTONBOX_START)

        lowerFrame.pack_start(vbbox, expand=False);
        
        self.inputbox = gtk.Entry(max=0)
        self.inputbox.show()
        self.inputbox.connect("activate", self.onEnterKey)
        upperFrame.pack_start(self.inputbox)

        self.searchButton = gtk.Button("Serach")
        self.searchButton.show()
        self.searchButton.connect("clicked", self.clicked_search)
        upperFrame.pack_end(self.searchButton)

        for i in range(self.num_buttons):
            self.buttons[i] = gtk.Button("---")
            self.buttons[i].set_use_underline(False)
            vbbox.pack_start(self.buttons[i])
            self.buttons[i].show()
            self.buttons[i].connect("clicked", self.clicked_cb)
        
        vbox = gtk.VBox()
        vbox.show()
        
        scrolledwin = gtk.ScrolledWindow()
        scrolledwin.show()
        self.textview = gtk.TextView()
        self.textview.show()
        self.textview.set_size_request(200,100)
        self.textview.set_wrap_mode(gtk.WRAP_CHAR)
        self.textbuffer = self.textview.get_buffer()
        scrolledwin.add(self.textview)
        lowerFrame.pack_start(scrolledwin)

        upperFrame.pack_start(vbox)
        
        wholeFrame = gtk.VBox()
        wholeFrame.show()
        wholeFrame.pack_start(upperFrame, expand=False)
        wholeFrame.pack_end(lowerFrame)
        
        self.window.add(wholeFrame)
        self.window.show()
        self.clipboard = gtk.clipboard_get(gtk.gdk.SELECTION_CLIPBOARD)
        gobject.timeout_add(1500, self.onTimer)
        
        self.lastWord = ""
        return

def main():
  gtk.main()
  return 0

if __name__ == '__main__':
    cbe = ClipboardExample()
    main()
