package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Channel;
import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.repository.ChannelRepository;
import com.softserve.academy.studhub.service.ChannelService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChannelServiceImplTest {

    @Mock
    private ChannelRepository channelRepository;

    private ChannelService channelService;

    @Before
    public void initialize() {
        channelService = new ChannelServiceImpl(channelRepository);
    }

    @After
    public void finalize() {
        channelService = null;
        channelRepository = null;
    }

    @Test
    public void savePositive() {
        Channel channel = new Channel();
        channel.setQuestion(new Question());

        when(channelRepository.saveAndFlush(channel)).thenReturn(channel);

        Assert.assertEquals(channelService.save(channel), channel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNullChannel() {
        channelService.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNullQuestionInChannel() {
        Channel channel = new Channel();
        channelService.save(channel);
    }

}